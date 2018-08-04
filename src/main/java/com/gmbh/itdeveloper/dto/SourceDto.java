package com.gmbh.itdeveloper.dto;

import org.hibernate.annotations.Type;

import javax.persistence.Column;

public class SourceDto {
    private String act_arr_date_time_lt; // actual arrival timestamp
    private String aircraft_name_scheduled; // scheduled aircraft name
    private String arr_apt_name_es; // Arrival airport name in Spanish
    private String arr_apt_code_iata; // IATA/ICAO code of arrival airport
    private String baggage_info; // Baggage information of flight todo LIFO logic
    private String carrier_airline_name_en; // Carrier airline name English
    private String carrier_icao_code; // Carrier IATA/ICAO code
    private String carrier_number; // Carrier number
    private String counter; // Registration counter todo LIFO logic
    private String dep_apt_name_es; // Departure airport name Spanish
    private String dep_apt_code_iata; // IATA/ICAO code of departure airport
    private String est_arr_date_time_lt; // estimated arrival timestamp
    private String est_dep_date_time_lt; // estimated departure timestamp
    private String flight_airline_name_en; // Flight airline name English
    private String flight_airline_name; // Flight Airline name
    private String flight_icao_code; // IATA/ICAO flight airline code
    private String flight_number; // flight number
    private String flt_leg_seq_no; // flight number
    private String gate_info; // gate information todo LIFO logic
    private String lounge_info; // lounge information todo LIFO logic
    private String schd_arr_only_date_lt; // scheduled arrival date
    private String schd_arr_only_time_lt; // scheduled arrival time
    private String source_data; // source of data
    private String status_info; // flight status
    private String terminal_info; // terminal information todo LIFO logic
    private String arr_terminal_info; // arrival terminal information todo LIFO logic
    private String act_dep_date_time_lt; // actual departure timestamp
    private String schd_dep_only_date_lt; // scheduled departure date
    private String schd_dep_only_time_lt; // scheduled departure time
    private Long created_at; // unix timestamp when record was created


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
