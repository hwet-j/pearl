package com.pits.auction.entity;

import jakarta.persistence.*;
import lombok.*;




/*  경매기간
테이블 : bidding_period

컬럼 :
    id : 입찰기간 번호 (Primary Key, Auto Increment)
    periodValue : 기간 (Not Null)
*/

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bidding_period")
public class BiddingPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period_value", nullable = false)
    private String periodValue;

}