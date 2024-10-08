package com.elite.entity.config;


import jakarta.persistence.*;

@Entity
@Table(name = "elite_country")
public class Country {

    @Id
    @SequenceGenerator(name = "elite_country_seq", sequenceName = "elite_country_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elite_country_seq")
    @Column(name = "country_id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "country_iso", nullable = false, unique = true)
    private String isp;

    @Column(name = "country_name", nullable = false, unique = true)
    private String name;

    @Column(name = "country_nicename", nullable = false, unique = true)
    private String niceName;

    @Column(name = "country_iso3")
    private String iso3;

    @Column(name = "country_numcode")
    private Long numCode;

    @Column(name = "country_phonecode", nullable = false, unique = true)
    private Long phoneCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Long getNumCode() {
        return numCode;
    }

    public void setNumCode(Long numCode) {
        this.numCode = numCode;
    }

    public Long getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Long phoneCode) {
        this.phoneCode = phoneCode;
    }
}
