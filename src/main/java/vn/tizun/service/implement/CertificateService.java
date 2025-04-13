package vn.tizun.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import vn.tizun.controller.request.CertificateGenerateRequest;
import vn.tizun.model.CourseEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class CertificateService {

    public byte[] generateCertificateImage(String fullName, String courseName) throws Exception {
        // Load ảnh mẫu từ resource folder
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/certificate-template.png");
        if (inputStream == null) {
            throw new FileNotFoundException("Không tìm thấy ảnh mẫu chứng chỉ!");
        }

        BufferedImage image = ImageIO.read(inputStream);

        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/GreatVibes-Regular.ttf");
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(64f); // 64f là size
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);
        g2d.setFont(customFont);
        g2d.setColor(Color.BLACK);

        FontMetrics metrics = g2d.getFontMetrics();
        int x = (image.getWidth() - metrics.stringWidth(fullName)) / 2;
        int y = 450;

        g2d.drawString(fullName, x, y);

        int xCourseName = (image.getWidth() - metrics.stringWidth(courseName)) / 2;
        int yCourseName = y + 150;
        g2d.drawString(courseName, xCourseName, yCourseName);
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    public String generateNftMetadata(CertificateGenerateRequest request, String certificateImageUrl) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode metadata = mapper.createObjectNode();

        metadata.put("name", "Chứng chỉ " + request.getCourseName());
        metadata.put("description", "NFT chứng nhận đã hoàn thành khóa học " + request.getCourseName() + " trên nền tảng Open Course.");
        metadata.put("image", certificateImageUrl);

        ArrayNode attributes = mapper.createArrayNode();
        attributes.add(createAttribute(mapper, "Family Name", request.getFamilyName()));
        attributes.add(createAttribute(mapper, "First Name", request.getFirstName()));
        attributes.add(createAttribute(mapper, "Course Name", request.getCourseName()));
        attributes.add(createAttribute(mapper, "Level", request.getLevel().toString()));
        attributes.add(createAttribute(mapper, "Date", request.getDate().toString()));
        metadata.set("attributes", attributes);

        try {
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(metadata); // Trả về chuỗi JSON
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ObjectNode createAttribute(ObjectMapper mapper, String traitType, String value) {
        ObjectNode attribute = mapper.createObjectNode();
        attribute.put("trait_type", traitType);
        attribute.put("value", value);
        return attribute;
    }
}
