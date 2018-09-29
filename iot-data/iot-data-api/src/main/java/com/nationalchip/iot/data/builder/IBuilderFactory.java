package com.nationalchip.iot.data.builder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/7/18 1:49 PM
 * @Modified:
 */
public interface IBuilderFactory {
    IUserBuilder user();
    IAssetBuilder asset();
    INewsBuilder news();
    IRoleBuilder role();

    IDocumentBuilder document();
}
