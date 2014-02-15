package com.eabax.hospital.integration.task.model;

public class Supplier {
  public Long id;
  public String number;
  public String name;
  public String contact;
  public String contactPhone;
  public String address;
  public String nature;
  public String legalPerson;
  
  public void setContact(String contact) {
    if (contact == null) {
      contact = "";
    }
    this.contact = contact;
  }
  public void setContactPhone(String contactPhone) {
    if (contactPhone == null) {
      contactPhone = "";
    }
    this.contactPhone = contactPhone;
  }
  public void setAddress(String address) {
    if (address == null) {
      address = "";
    }
    this.address = address;
  }
  public void setNature(String nature) {
    if (nature == null) {
      nature = "";
    }
    this.nature = nature;
  }
  public void setLegalPerson(String legalPerson) {
    if (legalPerson == null) {
      legalPerson = "";
    }
    this.legalPerson = legalPerson;
  }

  @Override
  public String toString() {
    return "Supplier [id=" + id + ", number=" + number + ", name=" + name
        + ", contact=" + contact + ", contactPhone=" + contactPhone
        + ", address=" + address + ", nature=" + nature + ", legalPerson="
        + legalPerson + "]";
  }
  
}
