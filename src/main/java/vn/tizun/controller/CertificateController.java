package vn.tizun.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import vn.tizun.controller.request.CertificateGenerateRequest;
import vn.tizun.service.IPFSService;
import vn.tizun.service.implement.CertificateService;
import vn.tizun.service.implement.EthereumService;

@RestController
@RequestMapping("/certificate")
@AllArgsConstructor
public class CertificateController {
    private CertificateService certificateService;
    private IPFSService ipfsService;
    private EthereumService ethereumService;

    @GetMapping("/generate")
    public ResponseEntity<String> getCertificate(@RequestBody CertificateGenerateRequest request) {
        try {
            byte[] imageBytes = certificateService.generateCertificateImage(request.getFamilyName() + ' ' + request.getFirstName(), request.getCourseName());
            String fileName = "certificate_" + request.getFamilyName() + ".png";
            String image_url = ipfsService.uploadImageByteToIPFS(imageBytes, fileName);

            String ipfs_url = ipfsService.uploadJSONToIPFS(certificateService.generateNftMetadata(request, image_url));

            return ResponseEntity.ok(ipfs_url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/mint")
    public ResponseEntity<?> mintNft() {
        try {
            String txHash = ethereumService.callMintFromBlockchain();
            return ResponseEntity.ok("Mint thành công! TxHash: " + txHash);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi mint NFT: " + e.getMessage());
        }
    }
}
