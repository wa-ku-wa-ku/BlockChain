package com.wetech.demo.web3j.service;

import com.wetech.demo.web3j.contracts.hgltoken.HGLToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

@Service
public class HGLTokenService {
    private static final Logger logger = LoggerFactory.getLogger(HGLTokenService.class);

    @Value("${web3j.client-address}")
    private String clientAddress;

    @Value("${web3j.private-key}")
    private String privateKey;

    private Web3j web3j;
    private Credentials credentials;

    // 部署合约
    public String deployContract() throws Exception {
        logger.info("开始部署 HGLToken 合约...");

        web3j = Web3j.build(new HttpService(clientAddress));
        credentials = Credentials.create(privateKey);

        // 部署合约，initialOwner 设置为当前账户
        HGLToken contract = HGLToken.deploy(
                web3j,
                credentials,
                new DefaultGasProvider(),
                credentials.getAddress()  // initialOwner
        ).send();

        String contractAddress = contract.getContractAddress();
        logger.info("合约部署成功，地址: {}", contractAddress);

        return contractAddress;
    }

    // Mint 代币
    public String mint(String contractAddress, String toAddress, BigInteger amount) throws Exception {
        logger.info("Mint 代币: to={}, amount={}", toAddress, amount);

        web3j = Web3j.build(new HttpService(clientAddress));
        credentials = Credentials.create(privateKey);

        HGLToken contract = HGLToken.load(contractAddress, web3j, credentials, new DefaultGasProvider());

        var receipt = contract.mint(toAddress, amount).send();
        String txHash = receipt.getTransactionHash();

        logger.info("Mint 成功，交易哈希: {}", txHash);
        return txHash;
    }

    // 查询余额
    public BigInteger balanceOf(String contractAddress, String address) throws Exception {
        logger.info("查询余额: address={}", address);

        web3j = Web3j.build(new HttpService(clientAddress));
        credentials = Credentials.create(privateKey);

        HGLToken contract = HGLToken.load(contractAddress, web3j, credentials, new DefaultGasProvider());

        BigInteger balance = contract.balanceOf(address).send();
        logger.info("余额: {}", balance);

        return balance;
    }

    // Transfer 转账
    public String transfer(String contractAddress, String toAddress, BigInteger amount) throws Exception {
        logger.info("转账: to={}, amount={}", toAddress, amount);

        web3j = Web3j.build(new HttpService(clientAddress));
        credentials = Credentials.create(privateKey);

        HGLToken contract = HGLToken.load(contractAddress, web3j, credentials, new DefaultGasProvider());

        var receipt = contract.transfer(toAddress, amount).send();
        String txHash = receipt.getTransactionHash();

        logger.info("转账成功，交易哈希: {}", txHash);
        return txHash;
    }

    // Approve 授权
    public String approve(String contractAddress, String spenderAddress, BigInteger amount) throws Exception {
        logger.info("授权: spender={}, amount={}", spenderAddress, amount);

        web3j = Web3j.build(new HttpService(clientAddress));
        credentials = Credentials.create(privateKey);

        HGLToken contract = HGLToken.load(contractAddress, web3j, credentials, new DefaultGasProvider());

        var receipt = contract.approve(spenderAddress, amount).send();
        String txHash = receipt.getTransactionHash();

        logger.info("授权成功，交易哈希: {}", txHash);
        return txHash;
    }

    // TransferFrom 代理转账
    public String transferFrom(String contractAddress, String fromAddress, String toAddress, BigInteger amount) throws Exception {
        logger.info("代理转账: from={}, to={}, amount={}", fromAddress, toAddress, amount);

        web3j = Web3j.build(new HttpService(clientAddress));
        credentials = Credentials.create(privateKey);

        HGLToken contract = HGLToken.load(contractAddress, web3j, credentials, new DefaultGasProvider());

        var receipt = contract.transferFrom(fromAddress, toAddress, amount).send();
        String txHash = receipt.getTransactionHash();

        logger.info("代理转账成功，交易哈希: {}", txHash);
        return txHash;
    }
}