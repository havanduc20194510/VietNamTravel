package com.haduc.go_travel_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ScheduleDetailId")
    private ScheduleDetail scheduleDetail;
    private String name;
    private String description;
    private Long star;
    private String reward;
    private Date deadline;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskTypeId")
    private TaskType taskType;
}