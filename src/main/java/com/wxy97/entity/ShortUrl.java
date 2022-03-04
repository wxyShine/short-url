package com.wxy97.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "short_url",indexes = {@Index(name = "idx_shorts",columnList = "shorts",unique = true)})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String shorts;

    @Lob @Basic(fetch = FetchType.LAZY) @Column(columnDefinition = "text")
    private String longUrl;

    private String shortUrl;

    @CreationTimestamp
    private LocalDateTime createTime;
}
