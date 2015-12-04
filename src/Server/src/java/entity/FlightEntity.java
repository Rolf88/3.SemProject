package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity(name = "Flight")
public class FlightEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String flightId;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date departure;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    private AirlineEntity airline;

    @OneToOne
    private AirportEntity origin;

    @OneToOne
    private AirportEntity destination;

    @OneToMany(mappedBy = "flight")
    private List<ReservationEntity> reservations = new ArrayList<>();

    public FlightEntity() {

    }

    public FlightEntity(Long id, String flightId, Date departure, int capacity, double price, AirlineEntity airline, AirportEntity origin, AirportEntity destination, List<ReservationEntity> reservations) {
        this.id = id;
        this.flightId = flightId;
        this.departure = departure;
        this.capacity = capacity;
        this.price = price;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.reservations = reservations;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AirlineEntity getAirline() {
        return airline;
    }

    public void setAirline(AirlineEntity airline) {
        this.airline = airline;
    }

    public AirportEntity getOrigin() {
        return origin;
    }

    public void setOrigin(AirportEntity origin) {
        this.origin = origin;
    }

    public AirportEntity getDestination() {
        return destination;
    }

    public void setDestination(AirportEntity destination) {
        this.destination = destination;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FlightEntity)) {
            return false;
        }
        FlightEntity other = (FlightEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FlightEntity[ id=" + id + " ]";
    }

}
