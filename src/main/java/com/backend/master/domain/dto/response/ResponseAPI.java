package com.backend.master.domain.dto.response;

public class ResponseAPI {
    private boolean success;
    private String messages;
    private Object result;
    private Object additionalEntity;

    public ResponseAPI(boolean success, String messages, Object result, Object additionalEntity) {
        this.success = success;
        this.messages = messages;
        this.result = result;
        this.additionalEntity = additionalEntity;
    }

    public ResponseAPI() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getAdditionalEntity() {
        return additionalEntity;
    }

    public void setAdditionalEntity(Object additionalEntity) {
        this.additionalEntity = additionalEntity;
    }

}
