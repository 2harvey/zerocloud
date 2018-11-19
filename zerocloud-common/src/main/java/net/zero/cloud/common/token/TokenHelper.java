package net.zero.cloud.common.token;

import io.jsonwebtoken.*;
import java.time.Instant;
import java.util.Date;


public class TokenHelper {

	private String webName = "ourdaxWeb";

	public String secret = "915fc714cf7000744c908d1bc140166f";

	private int expireTimeStampSecond = 1000 * 60 * 60 * 4;

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;


	public String  generateToken(TokenInfo tokenInfo) {
		long currentTimeStamp = Instant.now().getEpochSecond();
		String token = Jwts.builder().setIssuer(tokenInfo.getPlatformId())
				.setSubject(tokenInfo.getXId())
				.claim(TokenConstants.JWT_KEY_PLATFORMID, tokenInfo.getXId())// 在token中加入平台编号
				.claim(TokenConstants.JWT_KEY_NAME, tokenInfo.getName()) // token中包含用户名称或是微服务客户端名称
				.claim(TokenConstants.JWT_KEY_X_ID, tokenInfo.getXId())
				.claim(TokenConstants.JWT_KEY_SALTKEY, tokenInfo.getSaltKey())// 验证签名用的盐值
				.setAudience(tokenInfo.getPlatformId())
				.setIssuedAt(new Date(currentTimeStamp * 1000))
				.setExpiration(new Date(currentTimeStamp * 1000 + expireTimeStampSecond))
				.signWith(SIGNATURE_ALGORITHM, secret).compact();
		return token;
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public String refreshToken(String token) {
		String refreshedToken;
		long currentTimeStamp = Instant.now().getEpochSecond();
		Date a = new Date(currentTimeStamp * 1000);
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			claims.setIssuedAt(a);
			refreshedToken = Jwts.builder().setClaims(claims)
					.setExpiration(new Date(currentTimeStamp * 1000 + expireTimeStampSecond))
					.signWith(SIGNATURE_ALGORITHM, secret).compact();
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}



	/**
	 * 获取token中的用户信息
	 */
	public  TokenInfo getInfoFromToken(String token) throws Exception {
		Claims body = getAllClaimsFromToken(token);
		TokenInfo tokenInfo = TokenInfo.builder()
				.name(getObjectValue(body.get(TokenConstants.JWT_KEY_NAME)))
				.platformId(getObjectValue(body.get(TokenConstants.JWT_KEY_PLATFORMID)))
				.XId(getObjectValue(body.get(TokenConstants.JWT_KEY_X_ID)))
				.build();
		return tokenInfo;
	}

	public  String getObjectValue(Object obj){
		return obj==null?"":obj.toString();
	}

}
