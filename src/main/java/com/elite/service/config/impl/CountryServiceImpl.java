package com.elite.service.config.impl;

import com.elite.core.cache.CacheStoreList;
import com.elite.core.exception.ESFault;
import com.elite.core.exception.exceptions.NotFoundException;
import com.elite.core.factory.MessageResource;
import com.elite.core.log.ESLog;
import com.elite.entity.config.Country;
import com.elite.mapper.config.CountryMapper;
import com.elite.model.config.CountryModel;
import com.elite.model.config.CountryReqModel;
import com.elite.repository.config.CountryRepository;
import com.elite.service.config.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    private final CacheStoreList cacheStoreList;

    private static final String COUNTRY_CACHE = "COUNTRY_CACHE";

    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper, CacheStoreList cacheStoreList) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
        this.cacheStoreList = cacheStoreList;
    }

    @Override
    public List<CountryModel> getCountries() {
        log.info(MessageResource.getMessage(ESLog.ES_017));
        List<CountryModel> countries = new ArrayList<>();
        if (cacheStoreList.check(COUNTRY_CACHE)) {
            countries = cacheStoreList.get(COUNTRY_CACHE);
        } else {
            countries = countryRepository
                    .findAll()
                    .stream()
                    .map(countryMapper::convertCountrytoCountryModel)
                    .toList();
            cacheStoreList.add(COUNTRY_CACHE, countries);
        }
        return countries;
    }

    @Override
    public CountryModel createCountry(CountryReqModel countryReqModel) {
        Country country = countryMapper.convertCountryReqModelToCountry(countryReqModel);
        country = countryRepository.save(country);
        return countryMapper.convertCountrytoCountryModel(country);
    }

    @Override
    public CountryModel updateCountry(Long id, CountryReqModel countryReqModel) {
        countryRepository
                .findById(id).orElseThrow(() ->
                        new NotFoundException(MessageResource.getMessage(ESFault.ES_008)));
        Country country = countryMapper.convertCountryReqModelToCountry(countryReqModel);
        country.setId(id);
        country = countryRepository.save(country);
        return countryMapper.convertCountrytoCountryModel(country);
    }

    @Override
    public Boolean deleteCountry(Long id) {
        Country country = countryRepository
                .findById(id).orElseThrow(() ->
                        new NotFoundException(MessageResource.getMessage(ESFault.ES_008)));
        countryRepository.delete(country);
        return true;
    }

    @Override
    public CountryModel getCountryById(Long id) {
        log.info(MessageResource.getMessage(ESLog.ES_018), id);
        return countryMapper
                .convertCountrytoCountryModel(
                        countryRepository
                                .findById(id).orElseThrow(() ->
                                        new NotFoundException(MessageResource.getMessage(ESFault.ES_008))));
    }

    @Override
    public CountryModel getCountryByCode(String code) {
        log.info(MessageResource.getMessage(ESLog.ES_019), code);
        return countryMapper
                .convertCountrytoCountryModel(
                        countryRepository
                                .findByIsp(code).orElseThrow(() ->
                                        new NotFoundException(MessageResource.getMessage(ESFault.ES_008))));
    }

    @Override
    public Page<CountryModel> searchCountries(String searchTerm, int pageIndex, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Page<Country> countries = countryRepository.findByNiceNameContainingIgnoreCase(searchTerm, pageRequest);
        List<CountryModel> countryModels = countries.getContent().stream().map(countryMapper::convertCountrytoCountryModel).toList();
        return new PageImpl<>(countryModels, pageRequest, countries.getTotalElements());
    }
}
