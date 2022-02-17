package com.wxy97.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "short_url")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shorts;

    private String longUrl;

    private String shortUrl;

    @CreationTimestamp
    private LocalDateTime createTime;
}
