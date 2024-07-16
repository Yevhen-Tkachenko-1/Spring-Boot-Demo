package yevhent.demo.springboot.hotel.app.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "RESERVATION")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;
    @NonNull
    @Column(name = "ROOM_ID")
    private Long roomId;
    @NonNull
    @Column(name = "GUEST_ID")
    private Long guestId;
    @NonNull
    @Column(name = "TARGET_DATE")
    private Date targetDate;

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", guestId=" + guestId +
                ", targetDate=" + targetDate +
                '}';
    }
}