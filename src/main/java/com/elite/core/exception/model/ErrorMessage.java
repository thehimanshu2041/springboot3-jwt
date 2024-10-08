package com.elite.core.exception.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "ErrorMessage", description = "Error message")
public class ErrorMessage {

    @JsonProperty("status")
    private int status;

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("error")
    private Error error;
}
