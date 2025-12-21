package study.post.standard.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class Ut {

    /*
     * Ut.jwt.stString(secret, expireSeconds, body)
     * static으로 했기 때문에 따로 new Ut / new Ut jwt를 할 필요 없음
     */
    public static class jwt {
        public static String toString(String secret, long expireSeconds, Map<String, Object> body) {

            /*
             * ClaimsBuilder
             * - 이름 그대로 Claims 객체를 만들기 위한 설계도(Builder)
             * - 작성한 코드의 Jwts.claims()는 새로운 빌더를 생성
             * - .add(key, value)를 통해 원하는 데이터를 넣고, 마지막에 .build()를 호출하면 수정 불가능한 Claims 객체가 완성
             *
             * Claims
             * - JWT의 페이로드에 담기는 정보의 한 조각을 의미
             * - 예를 들어 "사용자 ID", "권한", "만료시간" 등이 각각 하나의 클레임
             * - Claims 객체는 이 정보들을 Map 형태로 들고 있는 읽기 전용 객체라고 보시면 됩니다.
             */

            ClaimsBuilder claimsBuilder = Jwts.claims();

            // "key" : "value" 추가
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                claimsBuilder.add(entry.getKey(), entry.getValue());
            }

            Claims claims = claimsBuilder.build();  // Claims 객체 생성

            Date issuedAt = new Date();
            Date expiration = new Date(issuedAt.getTime() + 1000L * expireSeconds);

            Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());

            String jwt = Jwts.builder()
                    .claims(claims)  // payload
                    .issuedAt(issuedAt)  // 발행일
                    .expiration(expiration)  // 만료일
                    .signWith(secretKey)  // 암호화 서명 (위조확인)
                    .compact();  // Base64 URL-Safe 문자열로 변환

            return jwt;
        }

        /*
         * Ut.jwt.isValid(jwt, secret)
         * jwt가 올바른지 (변조가 안되었는지) 확인하는 코드
         */
        public static boolean isValid(String jwt, String secret) {

            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            /*
             * secret 대조했을 때 다르면 Exception
             * 만료날짜 지났으면 Exception
             */
            try {
                // 파싱 (String -> jwt)
                Jwts
                        .parser()  // 파서 빌더 생성
                        .verifyWith(secretKey)  // 서명 검증 위한 키 설정
                        .build()  // 파서 객체 생성
                        .parse(jwt);  // JWT 토큰 해석 및 검증

            } catch (Exception e) {
                return false;
            }

            return true;
        }

        // String -> jwt의 payload
        public static Map<String, Object> toPayload(String jwt, String secret) {

            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            if(isValid(jwt, secret)) {
                return (Map<String, Object>) Jwts
                        .parser()
                        .verifyWith(secretKey)
                        .build()
                        .parse(jwt);
            }

            return null;
        }
    }
}
