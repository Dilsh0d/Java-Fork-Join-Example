package com.gmbh.itdeveloper.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "aenaflight_2017_01")
public class AenaflightSource2017Entity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "bigserial",nullable = false)
    private Long id;

    @Column(name = "act_arr_date_time_lt",length = 64)
    private String act_arr_date_time_lt; // actual arrival timestamp

    @Type(type = "text")
    @Column(name = "aircraft_name_scheduled")
    private String aircraft_name_scheduled; // scheduled aircraft name

    @Column(name = "arr_apt_name_es",length = 128)
    private String arr_apt_name_es; // Arrival airport name in Spanish

    @Column(name = "arr_apt_code_iata",length = 8)
    private String arr_apt_code_iata; // IATA/ICAO code of arrival airport

    @Column(name = "baggage_info",length = 128)
    private String baggage_info; // Baggage information of flight todo LIFO logic

    @Column(name = "carrier_airline_name_en",length = 128)
    private String carrier_airline_name_en; // Carrier airline name English

    @Column(name = "carrier_icao_code",length = 8)
    private String carrier_icao_code; // Carrier IATA/ICAO code

    @Column(name = "carrier_number",length = 8)
    private String carrier_number; // Carrier number

    @Column(name = "counter",length = 64)
    private String counter; // Registration counter todo LIFO logic

    @Column(name = "dep_apt_name_es",length = 128)
    private String dep_apt_name_es; // Departure airport name Spanish

    @Column(name = "dep_apt_code_iata",length = 8)
    private String dep_apt_code_iata; // IATA/ICAO code of departure airport

    @Column(name = "est_arr_date_time_lt",length = 64)
    private String est_arr_date_time_lt; // estimated arrival timestamp

    @Column(name = "est_dep_date_time_lt",length = 64)
    private String est_dep_date_time_lt; // estimated departure timestamp

    @Column(name = "flight_airline_name_en",length = 128)
    private String flight_airline_name_en; // Flight airline name English

    @Column(name = "flight_airline_name",length = 128)
    private String flight_airline_name; // Flight Airline name

    @Column(name = "flight_icao_code",length = 8)
    private String flight_icao_code; // IATA/ICAO flight airline code

    @Column(name = "flight_number",length = 8)
    private String flight_number; // flight number

    @Column(name = "flt_leg_seq_no",length = 8)
    private String flt_leg_seq_no; // flight number

    @Column(name = "gate_info",length = 128)
    private String gate_info; // gate information todo LIFO logic

    @Column(name = "lounge_info",length = 128)
    private String lounge_info; // lounge information todo LIFO logic

    @Column(name = "schd_arr_only_date_lt",length = 32)
    private String schd_arr_only_date_lt; // scheduled arrival date

    @Column(name = "schd_arr_only_time_lt",length = 32)
    private String schd_arr_only_time_lt; // scheduled arrival time

    @Type(type = "text")
    @Column(name = "source_data")
    private String source_data; // source of data

    @Column(name = "status_info",length = 128)
    private String status_info; // flight status

    @Column(name = "terminal_info",length = 128)
    private String terminal_info; // terminal information todo LIFO logic

    @Column(name = "arr_terminal_info",length = 128)
    private String arr_terminal_info; // arrival terminal information todo LIFO logic

    @Column(name = "act_dep_date_time_lt",length = 64)
    private String act_dep_date_time_lt; // actual departure timestamp

    @Column(name = "schd_dep_only_date_lt",length = 32)
    private String schd_dep_only_date_lt; // scheduled departure date

    @Column(name = "schd_dep_only_time_lt",length = 32)
    private String schd_dep_only_time_lt; // scheduled departure time

    @Column(name = "created_at")
    private Long created_at; // unix timestamp when record was created

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAct_arr_date_time_lt() {
        return act_arr_date_time_lt;
    }

    public void setAct_arr_date_time_lt(String act_arr_date_time_lt) {
        this.act_arr_date_time_lt = act_arr_date_time_lt;
    }

    public String getAircraft_name_scheduled() {
        return aircraft_name_scheduled;
    }

    public void setAircraft_name_scheduled(String aircraft_name_scheduled) {
        this.aircraft_name_scheduled = aircraft_name_scheduled;
    }

    public String getArr_apt_name_es() {
        return arr_apt_name_es;
    }

    public void setArr_apt_name_es(String arr_apt_name_es) {
        this.arr_apt_name_es = arr_apt_name_es;
    }

    public String getArr_apt_code_iata() {
        return arr_apt_code_iata;
    }

    public void setArr_apt_code_iata(String arr_apt_code_iata) {
        this.arr_apt_code_iata = arr_apt_code_iata;
    }

    public String getBaggage_info() {
        return baggage_info;
    }

    public void setBaggage_info(String baggage_info) {
        this.baggage_info = baggage_info;
    }

    public String getCarrier_airline_name_en() {
        return carrier_airline_name_en;
    }

    public void setCarrier_airline_name_en(String carrier_airline_name_en) {
        this.carrier_airline_name_en = carrier_airline_name_en;
    }

    public String getCarrier_icao_code() {
        return carrier_icao_code;
    }

    public void setCarrier_icao_code(String carrier_icao_code) {
        this.carrier_icao_code = carrier_icao_code;
    }

    public String getCarrier_number() {
        return carrier_number;
    }

    public void setCarrier_number(String carrier_number) {
        this.carrier_number = carrier_number;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getDep_apt_name_es() {
        return dep_apt_name_es;
    }

    public void setDep_apt_name_es(String dep_apt_name_es) {
        this.dep_apt_name_es = dep_apt_name_es;
    }

    public String getDep_apt_code_iata() {
        return dep_apt_code_iata;
    }

    public void setDep_apt_code_iata(String dep_apt_code_iata) {
        this.dep_apt_code_iata = dep_apt_code_iata;
    }

    public String getEst_arr_date_time_lt() {
        return est_arr_date_time_lt;
    }

    public void setEst_arr_date_time_lt(String est_arr_date_time_lt) {
        this.est_arr_date_time_lt = est_arr_date_time_lt;
    }

    public String getEst_dep_date_time_lt() {
        return est_dep_date_time_lt;
    }

    public void setEst_dep_date_time_lt(String est_dep_date_time_lt) {
        this.est_dep_date_time_lt = est_dep_date_time_lt;
    }

    public String getFlight_airline_name_en() {
        return flight_airline_name_en;
    }

    public void setFlight_airline_name_en(String flight_airline_name_en) {
        this.flight_airline_name_en = flight_airline_name_en;
    }

    public String getFlight_airline_name() {
        return flight_airline_name;
    }

    public void setFlight_airline_name(String flight_airline_name) {
        this.flight_airline_name = flight_airline_name;
    }

    public String getFlight_icao_code() {
        return flight_icao_code;
    }

    public void setFlight_icao_code(String flight_icao_code) {
        this.flight_icao_code = flight_icao_code;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getFlt_leg_seq_no() {
        return flt_leg_seq_no;
    }

    public void setFlt_leg_seq_no(String flt_leg_seq_no) {
        this.flt_leg_seq_no = flt_leg_seq_no;
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

    public String getSchd_arr_only_date_lt() {
        return schd_arr_only_date_lt;
    }

    public void setSchd_arr_only_date_lt(String schd_arr_only_date_lt) {
        this.schd_arr_only_date_lt = schd_arr_only_date_lt;
    }

    public String getSchd_arr_only_time_lt() {
        return schd_arr_only_time_lt;
    }

    public void setSchd_arr_only_time_lt(String schd_arr_only_time_lt) {
        this.schd_arr_only_time_lt = schd_arr_only_time_lt;
    }

    public String getSource_data() {
        return source_data;
    }

    public void setSource_data(String source_data) {
        this.source_data = source_data;
    }

    public String getStatus_info() {
        return status_info;
    }

    public void setStatus_info(String status_info) {
        this.status_info = status_info;
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

    public String getAct_dep_date_time_lt() {
        return act_dep_date_time_lt;
    }

    public void setAct_dep_date_time_lt(String act_dep_date_time_lt) {
        this.act_dep_date_time_lt = act_dep_date_time_lt;
    }

    public String getSchd_dep_only_date_lt() {
        return schd_dep_only_date_lt;
    }

    public void setSchd_dep_only_date_lt(String schd_dep_only_date_lt) {
        this.schd_dep_only_date_lt = schd_dep_only_date_lt;
    }

    public String getSchd_dep_only_time_lt() {
        return schd_dep_only_time_lt;
    }

    public void setSchd_dep_only_time_lt(String schd_dep_only_time_lt) {
        this.schd_dep_only_time_lt = schd_dep_only_time_lt;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }
}
