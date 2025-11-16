package com.wetech.demo.web3j.controller;

import com.wetech.demo.web3j.service.HGLTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/hgltoken")
public class HGLTokenController {

    @Autowired
    private HGLTokenService hglTokenService;

    // 部署合约
    @PostMapping("/deploy")
    public Map<String, Object> deploy() {
        Map<String, Object> result = new HashMap<>();
        try {
            String contractAddress = hglTokenService.deployContract();
            result.put("success", true);
            result.put("contractAddress", contractAddress);
            result.put("message", "合约部署成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }

    // Mint 代币
    @PostMapping("/mint")
    public Map<String, Object> mint(
            @RequestParam String contractAddress,
            @RequestParam String toAddress,
            @RequestParam String amount) {
        Map<String, Object> result = new HashMap<>();
        try {
            BigInteger amountBig = new BigInteger(amount);
            String txHash = hglTokenService.mint(contractAddress, toAddress, amountBig);
            result.put("success", true);
            result.put("transactionHash", txHash);
            result.put("message", "Mint 成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }

    // 查询余额
    @GetMapping("/balance")
    public Map<String, Object> balanceOf(
            @RequestParam String contractAddress,
            @RequestParam String address) {
        Map<String, Object> result = new HashMap<>();
        try {
            BigInteger balance = hglTokenService.balanceOf(contractAddress, address);
            result.put("success", true);
            result.put("balance", balance.toString());
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }

    // Transfer 转账
    @PostMapping("/transfer")
    public Map<String, Object> transfer(
            @RequestParam String contractAddress,
            @RequestParam String toAddress,
            @RequestParam String amount) {
        Map<String, Object> result = new HashMap<>();
        try {
            BigInteger amountBig = new BigInteger(amount);
            String txHash = hglTokenService.transfer(contractAddress, toAddress, amountBig);
            result.put("success", true);
            result.put("transactionHash", txHash);
            result.put("message", "转账成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }

    // Approve 授权
    @PostMapping("/approve")
    public Map<String, Object> approve(
            @RequestParam String contractAddress,
            @RequestParam String spenderAddress,
            @RequestParam String amount) {
        Map<String, Object> result = new HashMap<>();
        try {
            BigInteger amountBig = new BigInteger(amount);
            String txHash = hglTokenService.approve(contractAddress, spenderAddress, amountBig);
            result.put("success", true);
            result.put("transactionHash", txHash);
            result.put("message", "授权成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }

    // TransferFrom 代理转账
    @PostMapping("/transferFrom")
    public Map<String, Object> transferFrom(
            @RequestParam String contractAddress,
            @RequestParam String fromAddress,
            @RequestParam String toAddress,
            @RequestParam String amount) {
        Map<String, Object> result = new HashMap<>();
        try {
            BigInteger amountBig = new BigInteger(amount);
            String txHash = hglTokenService.transferFrom(contractAddress, fromAddress, toAddress, amountBig);
            result.put("success", true);
            result.put("transactionHash", txHash);
            result.put("message", "代理转账成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }
}