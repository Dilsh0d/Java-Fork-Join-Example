package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.dao.AenaflightDestinationDao;
import com.gmbh.itdeveloper.dao.AenaflightSource2017Dao;
import com.gmbh.itdeveloper.dto.AenaflightDto;
import com.gmbh.itdeveloper.entities.AenaflightDestinationEntity;
import com.gmbh.itdeveloper.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class LoadServiceImpl implements LoadService {

    @Autowired
    private AenaflightSource2017Dao aenaflightSourceDao;

    @Autowired
    private AenaflightDestinationDao aenaflightDestinationDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void readAndWriteTable(int index, int offset, int limit) {
        long b = System.currentTimeMillis();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Function<String, Date> dateFunction = new Function<String, Date>() {
            @Override
            public Date apply(String dateString) {
                if(StringUtils.isEmpty(dateString)) {
                    return null;
                } else {
                    try {
                        return formatter.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        Function<AenaflightDto, AenaflightDestinationEntity> entityFillFunction  = new Function<AenaflightDto, AenaflightDestinationEntity>() {

            public AenaflightDestinationEntity apply(AenaflightDto aenaflightDto) {
                AenaflightDestinationEntity aenaflightDestinationEntity = new AenaflightDestinationEntity();
                aenaflightDestinationEntity.setAdep(aenaflightDto.getDep_apt_code_iata()); // IATA/ICAO code of departure airport
                aenaflightDestinationEntity.setAdes(aenaflightDto.getArr_apt_code_iata()); //IATA/ICAO code of arrival airport
                aenaflightDestinationEntity.setFlight_code(aenaflightDto.getFlight_icao_code()); //IATA/ICAO flight airline code
                aenaflightDestinationEntity.setFlight_number(aenaflightDto.getFlight_number()); //flight number
                aenaflightDestinationEntity.setCarrier_code(aenaflightDto.getCarrier_icao_code()); //Carrier IATA/ICAO code
                aenaflightDestinationEntity.setCarrier_number(aenaflightDto.getCarrier_number()); // Carrier number
                aenaflightDestinationEntity.setStatus_info(aenaflightDto.getStatus_info()); // flight status
                if (!StringUtils.isEmpty(aenaflightDto.getSchd_dep_only_date_lt())
                        && !StringUtils.isEmpty(aenaflightDto.getSchd_dep_only_time_lt())) {
                    aenaflightDestinationEntity.
                            setSchd_dep_lt(
                                    dateFunction.apply(aenaflightDto.getSchd_dep_only_date_lt()
                                            + " " + aenaflightDto.getSchd_dep_only_time_lt())); // scheduled departure timestamp
                } else {
                    aenaflightDestinationEntity.setSchd_dep_lt(new Date());// do not null by default
                }
                if (!StringUtils.isEmpty(aenaflightDto.getSchd_arr_only_date_lt())
                        && !StringUtils.isEmpty(aenaflightDto.getSchd_arr_only_time_lt())) {
                    aenaflightDestinationEntity.setSchd_arr_lt(dateFunction.apply(aenaflightDto.getSchd_arr_only_date_lt()
                                            +" "+aenaflightDto.getSchd_arr_only_time_lt())); // scheduled arrival timestamp
                } else {
                    aenaflightDestinationEntity.setSchd_arr_lt(new Date()); // do not null by default
                }
                if(!StringUtils.isEmpty(aenaflightDto.getEst_dep_date_time_lt())) {
                    aenaflightDestinationEntity.setEst_dep_lt(dateFunction.apply(aenaflightDto.getEst_dep_date_time_lt())); // estimated departure timestamp
                }
                if(!StringUtils.isEmpty(aenaflightDto.getEst_arr_date_time_lt())) {
                    aenaflightDestinationEntity.setEst_arr_lt(dateFunction.apply(aenaflightDto.getEst_arr_date_time_lt())); // estimated arrival timestamp
                }
                if(!StringUtils.isEmpty(aenaflightDto.getAct_dep_date_time_lt())) {
                    aenaflightDestinationEntity.setAct_dep_lt(dateFunction.apply(aenaflightDto.getAct_dep_date_time_lt())); // actual departure timestamp
                }
                if(!StringUtils.isEmpty(aenaflightDto.getAct_arr_date_time_lt())) {
                    aenaflightDestinationEntity.setAct_arr_lt(dateFunction.apply(aenaflightDto.getAct_arr_date_time_lt())); // actual arrival timestamp
                }
                if(!StringUtils.isEmpty(aenaflightDto.getFlt_leg_seq_no())) {
                    aenaflightDestinationEntity.setFlt_leg_seq_no(Integer.parseInt(aenaflightDto.getFlt_leg_seq_no())); // flight leg sequence number
                }
                aenaflightDestinationEntity.setAircraft_name_scheduled(aenaflightDto.getAircraft_name_scheduled()); // scheduled aircraft name
                aenaflightDestinationEntity.setBaggage_info(aenaflightDto.getBaggage_info()); // baggage information
                aenaflightDestinationEntity.setCounter(aenaflightDto.getCounter()); // counter information
                aenaflightDestinationEntity.setGate_info(aenaflightDto.getGate_info()); // gate information
                aenaflightDestinationEntity.setLounge_info(aenaflightDto.getLounge_info()); // lounge information
                aenaflightDestinationEntity.setSource_data(aenaflightDto.getSource_data()); // source of data
                if(aenaflightDto.getCreated_at()!=null) {
                    aenaflightDestinationEntity.setCreated_at(new Date(aenaflightDto.getCreated_at().longValue())); // record creation timestamp
                }
                return aenaflightDestinationEntity;
            }
        };

        List<AenaflightDto> resultList = aenaflightSourceDao.getListStringByPagenation(index,offset,limit);
        List<AenaflightDestinationEntity>  insertedResult = resultList.stream()
                .map(entityFillFunction).collect(Collectors.<AenaflightDestinationEntity>toList());

        aenaflightDestinationDao.batchUpdates(insertedResult);

        long e = System.currentTimeMillis();
        System.out.println("Offset="+offset+ " Time = "+((e-b)/1000d));
    }
}
