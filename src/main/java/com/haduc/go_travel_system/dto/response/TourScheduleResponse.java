package com.haduc.go_travel_system.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TourScheduleResponse {
    private Long id;
    private String timeInDays;
    private String title;
    private List<ScheduleDetailResponse> scheduleDetail;
}