package model;

import java.security.PrivateKey;

public class UserData {
    private String userId;
    private String userName;
    private String marketId;
    private String marketName;
    private String marketTypeId;
    private String accessToken;
    private String tokenType;
    private String expiresAt;

    //Constructors
    public UserData(String string, String cursorString) {
    }

    public UserData(String userId, String userName, String marketId, String marketName, String marketTypeId, String accessToken, String tokenType, String expiresAt) {
        this.userId = userId;
        this.userName = userName;
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketTypeId = marketTypeId;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }


    public String getMarketId() {
        return marketId;
    }


    public String getMarketName() {
        return marketName;
    }


    public String getMarketTypeId() {
        return marketTypeId;
    }

    public String getAccessToken() {
        return accessToken;
    }


    public String getTokenType() {
        return tokenType;
    }


    public String getExpiresAt() {
        return expiresAt;
    }


}
