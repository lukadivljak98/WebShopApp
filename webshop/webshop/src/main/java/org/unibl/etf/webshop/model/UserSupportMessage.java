package org.unibl.etf.webshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_support_message")
public class UserSupportMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String message;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private AppUser sender;
    private boolean isRead;
}
