package com.thesis.thesis.infractructure.port;

import com.thesis.thesis.application.PDFDocumentDTO;
import org.springframework.data.domain.Page;

public interface DocumentPort {

    Page<PDFDocumentDTO> getAllDocuments(int pageIndex, int pageSize);
}
