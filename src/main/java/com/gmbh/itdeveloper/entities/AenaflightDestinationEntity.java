package com.gmbh.itdeveloper.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "aenaflight_source")
public class AenaflightDestinationEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "bigserial",nullable = false)
    private Long id;

    @Column(name = "adep",length = 8,nullable = false)
    private String adep; // IATA/ICAO code of departure airport

    @Column(name = "ades",length = 8,nullable = false)
    private String ades; // IATA/ICAO code of destination airport

    @Column(name = "flight_code",length = 8,nullable = false)
    private String flight_code; // ATA/ICAO airline code of flight

    @Column(name = "flight_number",length = 8,nullable = false)
    private String flight_number; //flight number

    @Column(name = "carrier_code",length = 8)
    private String carrier_code; // IATA/ICAO airline code of carrier

    @Column(name = "carrier_number",length = 8)
    private String carrier_number; // carrier number

    @Column(name = "status_info",length = 256,nullable = false)
    private String status_info; // flight status

    @Column(name = "schd_dep_lt", columnDefinition = "timestamp without time zone not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date schd_dep_lt; // scheduled departure timestamp

    @Column(name = "schd_arr_lt", columnDefinition = "timestamp without time zone not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date schd_arr_lt; // scheduled arrival timestamp

    @Column(name = "est_dep_lt", columnDefinition = "timestamp without time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private Date est_dep_lt; // estimated departure timestamp

    @Column(name = "est_arr_lt", columnDefinition = "timestamp without time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private Date est_arr_lt; // estimated arrival timestamp

    @Column(name = "act_dep_lt", columnDefinition = "timestamp without time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private Date act_dep_lt; // actual departure timestamp

    @Column(name = "act_arr_lt", columnDefinition = "timestamp without time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private Date act_arr_lt; // actual arrival timestamp

    @Column(name = "flt_leg_seq_no",nullable = false)
    private Integer flt_leg_seq_no; // flight leg sequence number

    @Type(type = "text")
    @Column(name = "aircraft_name_scheduled")
    private String aircraft_name_scheduled; // scheduled aircraft name

    @Column(name = "baggage_info",length = 128)
    private String baggage_info; // baggage information

    @Column(name = "counter",length = 128)
    private String counter; // counter information

    @Column(name = "gate_info",length = 128)
    private String gate_info; // gate information

    @Column(name = "lounge_info",length = 128)
    private String lounge_info; // lounge information

    @Column(name = "terminal_info",length = 128)
    private String terminal_info; // terminal information

    @Column(name = "arr_terminal_info",length = 128)
    private String arr_terminal_info; // arrival terminal information

    @Type(type = "text")
    @Column(name = "source_data")
    private String source_data; // source of data

    @Column(name = "created_at", columnDefinition = "timestamp without time zone not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at; // record creation timestamp

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdep() {
        return adep;
    }

    public void setAdep(String adep) {
        this.adep = adep;
    }

    public String getAdes() {
        return ades;
    }

    public void setAdes(String ades) {
        this.ades = ades;
    }

    public String getFlight_code() {
        return flight_code;
    }

    public void setFlight_code(String flight_code) {
        this.flight_code = flight_code;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getCarrier_code() {
        return carrier_code;
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = carrier_code;
    }

    public String getCarrier_number() {
        return carrier_number;
    }

    public void setCarrier_number(String carrier_number) {
        this.carrier_number = carrier_number;
    }

    public String getStatus_info() {
        return status_info;
    }

    public void setStatus_info(String status_info) {
        this.status_info = status_info;
    }

    public Date getSchd_dep_lt() {
        return schd_dep_lt;
    }

    public void setSchd_dep_lt(Date schd_dep_lt) {
        this.schd_dep_lt = schd_dep_lt;
    }

    public Date getSchd_arr_lt() {
        return schd_arr_lt;
    }

    public void setSchd_arr_lt(Date schd_arr_lt) {
        this.schd_arr_lt = schd_arr_lt;
    }

    public Date getEst_dep_lt() {
        return est_dep_lt;
    }

    public void setEst_dep_lt(Date est_dep_lt) {
        this.est_dep_lt = est_dep_lt;
    }

    public Date getEst_arr_lt() {
        return est_arr_lt;
    }

    public void setEst_arr_lt(Date est_arr_lt) {
        this.est_arr_lt = est_arr_lt;
    }

    public Date getAct_dep_lt() {
        return act_dep_lt;
    }

    public void setAct_dep_lt(Date act_dep_lt) {
        this.act_dep_lt = act_dep_lt;
    }

    public Date getAct_arr_lt() {
        return act_arr_lt;
    }

    public void setAct_arr_lt(Date act_arr_lt) {
        this.act_arr_lt = act_arr_lt;
    }

    public Integer getFlt_leg_seq_no() {
        return flt_leg_seq_no;
    }

    public void setFlt_leg_seq_no(Integer flt_leg_seq_no) {
        this.flt_leg_seq_no = flt_leg_seq_no;
    }

    public String getAircraft_name_scheduled() {
        return aircraft_name_scheduled;
    }

    public void setAircraft_name_scheduled(String aircraft_name_scheduled) {
        this.aircraft_name_scheduled = aircraft_name_scheduled;
    }

    public String getBaggage_info() {
        return baggage_info;
    }

    public void setBaggage_info(String baggage_info) {
        this.baggage_info = baggage_info;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getGate_info() {
        return gate_info;
    }

    public void setGate_info(String gate_info) {
        this.gate_info = gate_info;
    }

    public String getLounge_info() {
        return lounge_info;
    }

    public void setLounge_info(String lounge_info) {
        this.lounge_info = lounge_info;
    }

    public String getTerminal_info() {
        return terminal_info;
    }

    public void setTerminal_info(String terminal_info) {
        this.terminal_info = terminal_info;
    }

    public String getArr_terminal_info() {
        return arr_terminal_info;
    }

    public void setArr_terminal_info(String arr_terminal_info) {
        this.arr_terminal_info = arr_terminal_info;
    }

    public String getSource_data() {
        return source_data;
    }

    public void setSource_data(String source_data) {
        this.source_data = source_data;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}

