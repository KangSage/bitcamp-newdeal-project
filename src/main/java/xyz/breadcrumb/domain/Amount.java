package xyz.breadcrumb.domain;

public class Amount {
    protected int no;
    protected int mno;
    protected String type;
    protected String history;
    protected String amount;
    protected String category;
    protected String memo;
    protected String date;
    
    @Override
    public String toString() {
        return "Amount [no=" + no + ", mno=" + mno + ", type=" + type + ", history=" + history + ", amount=" + amount
                + ", category=" + category + ", memo=" + memo + ", date=" + date + "]";
    }
    
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public int getMno() {
        return mno;
    }
    public void setMno(int mno) {
        this.mno = mno;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getHistory() {
        return history;
    }
    public void setHistory(String history) {
        this.history = history;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
}
