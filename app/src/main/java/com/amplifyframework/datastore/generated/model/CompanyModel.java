package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the CompanyModel type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "CompanyModels", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class CompanyModel implements Model {
  public static final QueryField ID = field("CompanyModel", "id");
  public static final QueryField TICKER = field("CompanyModel", "ticker");
  public static final QueryField REGION = field("CompanyModel", "region");
  public static final QueryField PRICE_TODAY = field("CompanyModel", "priceToday");
  public static final QueryField PRICE_YEST = field("CompanyModel", "priceYest");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String ticker;
  private final @ModelField(targetType="String") String region;
  private final @ModelField(targetType="String") String priceToday;
  private final @ModelField(targetType="String") String priceYest;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTicker() {
      return ticker;
  }
  
  public String getRegion() {
      return region;
  }
  
  public String getPriceToday() {
      return priceToday;
  }
  
  public String getPriceYest() {
      return priceYest;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private CompanyModel(String id, String ticker, String region, String priceToday, String priceYest) {
    this.id = id;
    this.ticker = ticker;
    this.region = region;
    this.priceToday = priceToday;
    this.priceYest = priceYest;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      CompanyModel companyModel = (CompanyModel) obj;
      return ObjectsCompat.equals(getId(), companyModel.getId()) &&
              ObjectsCompat.equals(getTicker(), companyModel.getTicker()) &&
              ObjectsCompat.equals(getRegion(), companyModel.getRegion()) &&
              ObjectsCompat.equals(getPriceToday(), companyModel.getPriceToday()) &&
              ObjectsCompat.equals(getPriceYest(), companyModel.getPriceYest()) &&
              ObjectsCompat.equals(getCreatedAt(), companyModel.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), companyModel.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTicker())
      .append(getRegion())
      .append(getPriceToday())
      .append(getPriceYest())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("CompanyModel {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("ticker=" + String.valueOf(getTicker()) + ", ")
      .append("region=" + String.valueOf(getRegion()) + ", ")
      .append("priceToday=" + String.valueOf(getPriceToday()) + ", ")
      .append("priceYest=" + String.valueOf(getPriceYest()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static CompanyModel justId(String id) {
    return new CompanyModel(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      ticker,
      region,
      priceToday,
      priceYest);
  }
  public interface BuildStep {
    CompanyModel build();
    BuildStep id(String id);
    BuildStep ticker(String ticker);
    BuildStep region(String region);
    BuildStep priceToday(String priceToday);
    BuildStep priceYest(String priceYest);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String ticker;
    private String region;
    private String priceToday;
    private String priceYest;
    @Override
     public CompanyModel build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new CompanyModel(
          id,
          ticker,
          region,
          priceToday,
          priceYest);
    }
    
    @Override
     public BuildStep ticker(String ticker) {
        this.ticker = ticker;
        return this;
    }
    
    @Override
     public BuildStep region(String region) {
        this.region = region;
        return this;
    }
    
    @Override
     public BuildStep priceToday(String priceToday) {
        this.priceToday = priceToday;
        return this;
    }
    
    @Override
     public BuildStep priceYest(String priceYest) {
        this.priceYest = priceYest;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String ticker, String region, String priceToday, String priceYest) {
      super.id(id);
      super.ticker(ticker)
        .region(region)
        .priceToday(priceToday)
        .priceYest(priceYest);
    }
    
    @Override
     public CopyOfBuilder ticker(String ticker) {
      return (CopyOfBuilder) super.ticker(ticker);
    }
    
    @Override
     public CopyOfBuilder region(String region) {
      return (CopyOfBuilder) super.region(region);
    }
    
    @Override
     public CopyOfBuilder priceToday(String priceToday) {
      return (CopyOfBuilder) super.priceToday(priceToday);
    }
    
    @Override
     public CopyOfBuilder priceYest(String priceYest) {
      return (CopyOfBuilder) super.priceYest(priceYest);
    }
  }
  
}
