package a01183994.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Customer {
    private final String id;
    private final String phone;
    private String firstName;
    private String lastName;
    private String streetName;
    private String city;
    private String postalCode;
    private String email;
    private LocalDate joinDate;

    public static class Builder {
        // Required parameters
        private final String id;
        private final String phone;

        // Optional parameters - initialized to default values
        private String firstName = "";
        private String lastName = "";
        private String streetName = "";
        private String city = "";
        private String postalCode = "";
        private String email = "";
        private LocalDate joinDate = LocalDate.now();  // Default to current date

        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");


        public Builder(String id, String phone) {
            this.id = id;
            this.phone = phone;
        }

        public Builder setFirstName(String val) {
            firstName = val;
            return this;
        }

        public Builder setLastName(String val) {
            lastName = val;
            return this;
        }

        public Builder setStreetName(String val) {
            streetName = val;
            return this;
        }

        public Builder setCity(String val) {
            city = val;
            return this;
        }

        public Builder setPostalCode(String val) {
            postalCode = val;
            return this;
        }

        public Builder setEmail(String val) {
            email = val;
            return this;
        }

        public Builder setJoinDate(String val) {
            try {
                if (val.contains("-")) {
                    // If the date is in YYYY-MM-DD format
                    this.joinDate = LocalDate.parse(val);
                } else {
                    // If it's in YYYYMMDD format
                    this.joinDate = LocalDate.parse(val, DATE_FORMATTER);
                }
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD or YYYYMMDD.", e);
            }
            return this;
        }
        
        public Customer build() {
            return new Customer(this);
        }
    }

    private Customer(Builder builder) {
        id = builder.id;
        phone = builder.phone;
        firstName = builder.firstName;
        lastName = builder.lastName;
        streetName = builder.streetName;
        city = builder.city;
        postalCode = builder.postalCode;
        email = builder.email;
        joinDate = builder.joinDate;
    }

    
    public String getId() {
		return id;
	}


	public String getPhone() {
		return phone;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public String getStreetName() {
		return streetName;
	}


	public String getCity() {
		return city;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public String getEmail() {
		return email;
	}


	public LocalDate getJoinDate() {
		return joinDate;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public void setEmail(String email) {
		this.email = email;
	}


    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }


	@Override
    public String toString() {
        return "Customer [id=" + id + ", phone=" + phone + ", firstName=" + firstName + ", lastName=" + lastName
                + ", streetName=" + streetName + ", city=" + city + ", postalCode=" + postalCode + ", email=" + email
                + ", joinDate=" + joinDate + "]";
    }
}