package com.lawencon.jobportal.model.request;

import lombok.Data;

@Data
public class UpgradeAplicationStageRequest {
    private String appliacationId;
    private String stageId;
}
