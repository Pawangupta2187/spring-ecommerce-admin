package com.admin.admin.entities.orders;


public enum Status {
    ORDER_PLACED, DELIVERED,CANCELLED, ORDER_REJECTED, ORDER_CONFIRMED,
    ORDER_SHIPPED, RETURN_REQUESTED, RETURN_REJECTED,
    RETURN_APPROVED, PICK_UP_INITIATED, PICK_UP_COMPLETED,
    REFUND_INITIATED, REFUND_COMPLETED, CLOSED;

    Status() {
    }
}