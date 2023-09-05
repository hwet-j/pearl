package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicAuctionProjection;
import com.pits.auction.auth.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicAuctionRepository extends JpaRepository<MusicAuction, Long> {

    @Query("SELECT m FROM MusicAuction m WHERE m.status = 'Active'")
    List<MusicAuction> findAllActiveAuctions();

    Page<MusicAuction> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT ma FROM MusicAuction ma ORDER BY ma.endTime DESC limit 5")
    List<MusicAuction> findAllByOrderByEndTime();

    @Modifying
    @Query("UPDATE MusicAuction ma SET ma.status = :status WHERE ma.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") String status);

    
    /*경매가 아직 안끝난것중에 가격 최상위 5개 */
    @Query(value = "SELECT new com.pits.auction.auctionBoard.entity.MusicAuctionProjection(" +
            "M.id AS id, " +
            "M.title AS title, " +
            "(SELECT MAX(B.price) FROM Bidding B WHERE B.auctionId = M.id GROUP BY B.auctionId) AS maxPrice, " +
            "M.endTime AS endTime, " +
            "M.albumImage AS albumImage, " +
            "M.albumMusic AS albumMusic, " +
            "M.authorNickname AS authorNickname) " +  // 여기에 authorNickname 필드를 추가
            "FROM MusicAuction M " +
            "WHERE M.endTime > CURRENT_TIMESTAMP " +
            "ORDER BY maxPrice DESC",
            countQuery = "SELECT COUNT(M.id) " +
                    "FROM MusicAuction M " +
                    "WHERE M.endTime > CURRENT_TIMESTAMP")
    Page<MusicAuctionProjection> findTop5ByEndTimeAfterCurrent(Pageable pageable);




}
