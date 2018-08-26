package xyz.breadcrumb.domain;

public class Amount {

    private int no;
    private int memberNo;
    private String amountType;
    private String history;
    private int amount;
    private String category;
    private String memo;
    private String happenDate;
    private String createdDate;

    @Override
    public String toString() {
        return "Amount{" +
                "no=" + no +
                ", memberNo=" + memberNo +
                ", amountType='" + amountType + '\'' +
                ", history='" + history + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", memo='" + memo + '\'' +
                ", happenDate='" + happenDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(String happenDate) {
        this.happenDate = happenDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
