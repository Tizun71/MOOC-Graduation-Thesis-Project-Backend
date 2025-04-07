package vn.tizun.service.implement;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class CertificateService {

    public byte[] generateCertificate(String fullName) throws Exception {
        // Load ảnh mẫu từ resource folder
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/certificate-template.png");
        if (inputStream == null) {
            throw new FileNotFoundException("Không tìm thấy ảnh mẫu chứng chỉ!");
        }

        BufferedImage image = ImageIO.read(inputStream);

        // Tạo đối tượng vẽ lên ảnh
        Graphics2D g2d = image.createGraphics();

        // Anti-aliasing cho chữ đẹp
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Chọn font và màu chữ
        InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/GreatVibes-Regular.ttf");
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(64f); // 64f là size
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);
        g2d.setFont(customFont);
        g2d.setColor(Color.BLACK);

        // Tính toán vị trí vẽ tên
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (image.getWidth() - metrics.stringWidth(fullName)) / 2;
        int y = 450; // tùy chỉnh vị trí chữ

        // Vẽ tên lên ảnh
        g2d.drawString(fullName, x, y);
        g2d.drawString("Blockchain cơ bản", x, y + 150);
        g2d.dispose();

        // Ghi ảnh ra dạng mảng byte để trả về
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
