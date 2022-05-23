package doc.student.fitnesstrainerapp.Entities;

public abstract class PersonEntity {
    private String name;
    private String memberStartDate;


    public PersonEntity(String name, String memberStartDate) {
        this.name = name;
        this.memberStartDate = memberStartDate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMemberStartDate() {
        return memberStartDate;
    }

    public void setMemberStartDate(String memberStartDate) {
        this.memberStartDate = memberStartDate;
    }
}
