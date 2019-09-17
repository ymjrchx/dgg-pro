package net.dgg.dqc.backservice.entity.parse;

import java.util.List;

/**
 * 查询开庭公告-开庭公告详情查询
 * Created by jiangsh on 2018/6/5 11:29
 */
public class Ktgg {
    private String province; //省份
    private String case_Reason; //案由
    private String schedule_Time; //排期日期
    private String execute_Gov; //法院
    private String undertake_Department; //承办部门
    private String execute_Unite; //法庭
    private String chief_Judge; //审判长/主审人
    private String open_Time; //开庭日期
    private String case_No; //案号
    private String person; //当事人

    private List<Prosecutor> prosecutor; //上诉人
    private List<Defendant> defendant; //被上诉人

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCase_Reason() {
        return case_Reason;
    }

    public void setCase_Reason(String case_Reason) {
        this.case_Reason = case_Reason;
    }

    public String getSchedule_Time() {
        return schedule_Time;
    }

    public void setSchedule_Time(String schedule_Time) {
        this.schedule_Time = schedule_Time;
    }

    public String getExecute_Gov() {
        return execute_Gov;
    }

    public void setExecute_Gov(String execute_Gov) {
        this.execute_Gov = execute_Gov;
    }

    public String getUndertake_Department() {
        return undertake_Department;
    }

    public void setUndertake_Department(String undertake_Department) {
        this.undertake_Department = undertake_Department;
    }

    public String getExecute_Unite() {
        return execute_Unite;
    }

    public void setExecute_Unite(String execute_Unite) {
        this.execute_Unite = execute_Unite;
    }

    public String getChief_Judge() {
        return chief_Judge;
    }

    public void setChief_Judge(String chief_Judge) {
        this.chief_Judge = chief_Judge;
    }

    public String getOpen_Time() {
        return open_Time;
    }

    public void setOpen_Time(String open_Time) {
        this.open_Time = open_Time;
    }

    public String getCase_No() {
        return case_No;
    }

    public void setCase_No(String case_No) {
        this.case_No = case_No;
    }

    public List<Prosecutor> getProsecutor() {
        return prosecutor;
    }

    public void setProsecutor(List<Prosecutor> prosecutor) {
        this.prosecutor = prosecutor;
    }

    public List<Defendant> getDefendant() {
        return defendant;
    }

    public void setDefendant(List<Defendant> defendant) {
        this.defendant = defendant;
    }


    public static class Prosecutor{
        private String name; //上诉人
        private String keyNo; //KeyNo

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKeyNo() {
            return keyNo;
        }

        public void setKeyNo(String keyNo) {
            this.keyNo = keyNo;
        }
    }

    public static class Defendant{
        private String name; //被上诉人
        private String keyNo; //KeyNo

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKeyNo() {
            return keyNo;
        }

        public void setKeyNo(String keyNo) {
            this.keyNo = keyNo;
        }
    }
}


