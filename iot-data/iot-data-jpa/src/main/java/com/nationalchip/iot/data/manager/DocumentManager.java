package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.Document;
import com.nationalchip.iot.data.model.IDocument;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/4/18 10:20 AM
 * @Modified:
 */
@Component
public class DocumentManager extends NamedManager<IDocument,Document> implements IDocumentManager {
}
