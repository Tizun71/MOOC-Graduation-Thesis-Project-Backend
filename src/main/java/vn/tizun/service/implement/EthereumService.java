package vn.tizun.service.implement;

import org.springframework.beans.factory.annotation.Value;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;


import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

@Service
public class EthereumService {

    @Value("${eth.walletPrivateKey}")
    private String walletPrivateKey;

    @Value("${eth.sepoliaNode}")
    private String sepoliaNode;

    public String callMintFromBlockchain() throws IOException {
        String privateKey = walletPrivateKey;
        String contractAddress = "0x48D0FC315F4d0c6B6674d8f9EF1F19024f338636";

        Web3j web3j = Web3j.build(new HttpService(sepoliaNode));
        Credentials credentials = Credentials.create(privateKey);

// Lấy nonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

// ⚠️ Tham số truyền vào
        int nftTypeValue = 0; // ví dụ BASIC = 0
        String metadataURI = "QmYourMetadataHashHere";

// Tạo function mint(uint8,string)
        Function function = new Function(
                "mint",
                Arrays.asList(new Uint8(BigInteger.valueOf(nftTypeValue)), new Utf8String(metadataURI)),
                Collections.emptyList()
        );
        String encodedFunction = FunctionEncoder.encode(function);

// Tạo RawTransaction
        BigInteger gasLimit = BigInteger.valueOf(300_000);
        BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress,
                BigInteger.ZERO,
                encodedFunction
        );

// Ký và gửi giao dịch
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, 11155111L, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction sendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        if (sendTransaction.hasError()) {
            System.err.println("Lỗi: " + sendTransaction.getError().getMessage());
            return "";
        } else {
            System.out.println("Gửi mint thành công! TxHash: " + sendTransaction.getTransactionHash());
            return sendTransaction.getTransactionHash();
        }
    }
}
