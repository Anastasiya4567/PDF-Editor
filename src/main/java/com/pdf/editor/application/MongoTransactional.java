package com.pdf.editor.application;


import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Transactional(transactionManager = MongoTransactional.MONGO_TRANSACTIONAL_MANAGER_NAME)
public @interface MongoTransactional {

    String MONGO_TRANSACTIONAL_MANAGER_NAME = "mongoTransactionalManager";
}
