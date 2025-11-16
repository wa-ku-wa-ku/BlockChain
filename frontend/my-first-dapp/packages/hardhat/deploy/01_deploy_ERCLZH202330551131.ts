import { HardhatRuntimeEnvironment } from "hardhat/types";
import { DeployFunction } from "hardhat-deploy/types";
import { Contract } from "ethers";

const deployHGLToken: DeployFunction = async function (hre: HardhatRuntimeEnvironment) {
  const { deployer } = await hre.getNamedAccounts();
  const { deploy } = hre.deployments;

  // 部署 HGLToken，传入构造参数 initialOwner
  await deploy("HGLToken", {
    from: deployer,
    args: ["0xaCA1Dde5FFc624992f98654E2CF4E930eC844BeB"],
    log: true,
    autoMine: true,
  });

  const hglToken = await hre.ethers.getContract<Contract>("HGLToken", deployer);
  console.log("部署成功，合约地址:", hglToken.target);
};

export default deployHGLToken;

// 修改部署标签
deployHGLToken.tags = ["ERCLZH202330551131"];
