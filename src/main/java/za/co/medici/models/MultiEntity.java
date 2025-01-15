package za.co.medici.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class MultiEntity {

    @NotNull
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @NotNull
    @Column(name = "last_updated_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "last_updated_by", nullable = false)
    private String lastUpdatedBy;

    @Column(name = "deleted_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deletedDate;
}