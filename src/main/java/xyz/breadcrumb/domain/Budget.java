package xyz.breadcrumb.domain;

public class Budget {
    
    private int budgetNo;
    private int memberNo;
    private String month;
    private int amount;
    private int withdraw;
    public int getBudgetNo() {
        return budgetNo;
    }
    public void setBudgetNo(int budgetNo) {
        this.budgetNo = budgetNo;
    }
    public int getMemberNo() {
        return memberNo;
    }
    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getWithdraw() {
        return withdraw;
    }
    public void setWithdraw(int withdraw) {
        this.withdraw = withdraw;
    }
    @Override
    public String toString() {
        return "Budget [budgetNo=" + budgetNo + ", memberNo=" + memberNo + ", month=" + month + ", amount=" + amount
                + ", withdraw=" + withdraw + "]";
    }
    
    
    
    
}
