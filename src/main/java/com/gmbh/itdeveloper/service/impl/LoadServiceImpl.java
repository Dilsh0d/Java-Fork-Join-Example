package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.App;
import com.gmbh.itdeveloper.dao.AenaflightDestinationDao;
import com.gmbh.itdeveloper.dao.AenaflightSource2017Dao;
import com.gmbh.itdeveloper.dao.PositionConfigDao;
import com.gmbh.itdeveloper.entities.AenaflightDestinationEntity;
import com.gmbh.itdeveloper.entities.AenaflightSource2017Entity;
import com.gmbh.itdeveloper.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LoadServiceImpl implements LoadService {

    @Autowired
    private AenaflightSource2017Dao aenaflightSourceDao;

    @Autowired
    private AenaflightDestinationDao aenaflightDestinationDao;

    @Autowired
    private PositionConfigDao positionConfigDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void readAndWriteTable(int index, int offset, int limit) {
        long b = System.currentTimeMillis();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Function<String, Date> dateFunction = new Function<String, Date>() {
            @Override
            public Date apply(String dateString) {
                if(StringUtils.isEmpty(dateString)) {
                    return null;
                } else {
                    try {
                        GregorianCalendar gregorianCalendar = new GregorianCalendar();
                        gregorianCalendar.setTime(formatter.parse(dateString));
                        gregorianCalendar.add(GregorianCalendar.YEAR,2000);
                        return  gregorianCalendar.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        Function<AenaflightSource2017Entity, AenaflightDestinationEntity> entityFillFunction  = new Function<AenaflightSource2017Entity, AenaflightDestinationEntity>() {

            public AenaflightDestinationEntity apply(AenaflightSource2017Entity aenaflight2017Source) {
                AenaflightDestinationEntity aenaflightDestinationEntity = new AenaflightDestinationEntity();
                aenaflightDestinationEntity.setAdep(aenaflight2017Source.getDep_apt_code_iata()); // IATA/ICAO code of departure airport
                aenaflightDestinationEntity.setAdes(aenaflight2017Source.getArr_apt_code_iata()); //IATA/ICAO code of arrival airport
                aenaflightDestinationEntity.setFlight_code(aenaflight2017Source.getFlight_icao_code()); //IATA/ICAO flight airline code
                aenaflightDestinationEntity.setFlight_number(aenaflight2017Source.getFlight_number()); //flight number
                aenaflightDestinationEntity.setCarrier_code(aenaflight2017Source.getCarrier_icao_code()); //Carrier IATA/ICAO code
                aenaflightDestinationEntity.setCarrier_number(aenaflight2017Source.getCarrier_number()); // Carrier number
                aenaflightDestinationEntity.setStatus_info(aenaflight2017Source.getStatus_info()); // flight status
                if (!StringUtils.isEmpty(aenaflight2017Source.getSchd_dep_only_date_lt())
                        && !StringUtils.isEmpty(aenaflight2017Source.getSchd_dep_only_time_lt())) {
                    aenaflightDestinationEntity.
                            setSchd_dep_lt(
                                    dateFunction.apply(aenaflight2017Source.getSchd_dep_only_date_lt()
                                            + " " + aenaflight2017Source.getSchd_dep_only_time_lt())); // scheduled departure timestamp
                } else {
                    aenaflightDestinationEntity.setSchd_dep_lt(new Date());// do not null by default
                }
                if (!StringUtils.isEmpty(aenaflight2017Source.getSchd_arr_only_date_lt())
                        && !StringUtils.isEmpty(aenaflight2017Source.getSchd_arr_only_time_lt())) {
                    aenaflightDestinationEntity.setSchd_arr_lt(dateFunction.apply(aenaflight2017Source.getSchd_arr_only_date_lt()
                                            +" "+aenaflight2017Source.getSchd_arr_only_time_lt())); // scheduled arrival timestamp
                } else {
                    aenaflightDestinationEntity.setSchd_arr_lt(new Date()); // do not null by default
                }
                if(!StringUtils.isEmpty(aenaflight2017Source.getEst_dep_date_time_lt())) {
                    aenaflightDestinationEntity.setEst_dep_lt(dateFunction.apply(aenaflight2017Source.getEst_dep_date_time_lt())); // estimated departure timestamp
                }
                if(!StringUtils.isEmpty(aenaflight2017Source.getEst_arr_date_time_lt())) {
                    aenaflightDestinationEntity.setEst_arr_lt(dateFunction.apply(aenaflight2017Source.getEst_arr_date_time_lt())); // estimated arrival timestamp
                }
                if(!StringUtils.isEmpty(aenaflight2017Source.getAct_dep_date_time_lt())) {
                    aenaflightDestinationEntity.setAct_dep_lt(dateFunction.apply(aenaflight2017Source.getAct_dep_date_time_lt())); // actual departure timestamp
                }
                if(!StringUtils.isEmpty(aenaflight2017Source.getAct_arr_date_time_lt())) {
                    aenaflightDestinationEntity.setAct_arr_lt(dateFunction.apply(aenaflight2017Source.getAct_arr_date_time_lt())); // actual arrival timestamp
                }
                if(!StringUtils.isEmpty(aenaflight2017Source.getFlt_leg_seq_no())) {
                    aenaflightDestinationEntity.setFlt_leg_seq_no(Integer.parseInt(aenaflight2017Source.getFlt_leg_seq_no())); // flight leg sequence number
                }
                aenaflightDestinationEntity.setAircraft_name_scheduled(aenaflight2017Source.getAircraft_name_scheduled()); // scheduled aircraft name
                aenaflightDestinationEntity.setBaggage_info(aenaflight2017Source.getBaggage_info()); // baggage information
                aenaflightDestinationEntity.setCounter(aenaflight2017Source.getCounter()); // counter information
                aenaflightDestinationEntity.setGate_info(aenaflight2017Source.getGate_info()); // gate information
                aenaflightDestinationEntity.setLounge_info(aenaflight2017Source.getLounge_info()); // lounge information
                aenaflightDestinationEntity.setSource_data(aenaflight2017Source.getSource_data()); // source of data
                if(aenaflight2017Source.getCreated_at()!=null) {
                    Timestamp stamp = new Timestamp(aenaflight2017Source.getCreated_at().longValue());
                    aenaflightDestinationEntity.setCreated_at(new Date(stamp.getTime())); // record creation timestamp
                }
                return aenaflightDestinationEntity;
            }
        };

        List<AenaflightSource2017Entity> resultList = aenaflightSourceDao.getListByPagenation(offset,limit);
        List<AenaflightDestinationEntity>  insertedResult = resultList.stream()
                .map(entityFillFunction).collect(Collectors.<AenaflightDestinationEntity>toList());

        aenaflightDestinationDao.batchInserts(insertedResult);

        positionConfigDao.insertOffset((offset+App.LIMIT)/ App.LIMIT);

        long e = System.currentTimeMillis();
        System.out.println("Offset="+offset+ " Time = "+((e-b)/1000d));
    }
}
