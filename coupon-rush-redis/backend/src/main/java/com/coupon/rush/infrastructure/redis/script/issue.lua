
-- KEYS[1] = stockKey
-- KEYS[2] = issuedUSersKey
-- KEYS[3] = userLockKey
-- ARGV[1] = userID
-- ARGV[2] = lockTtlSec

-- 1) 중복 요청 방지
local lock = redis.call("SETNX", KEYS[3], "1")
if lock == 0 then
    return {0, "DUPLICATE_ATTEMPT"}
end
redis.call("EXPIRE", KEYS[3], ARGV[2])

-- 2) 이미 발급받은 유저인지 체크
if redis.call("SISMEMBER", KEYS[2], ARGV[1]) == 1 then
    return {0, "ALREADY_ISSUED"}
end

-- 3) 재고 확인
local stock = tonumber(redis.call("GET", KEYS[1]) or "-1")
if stock <= 0 then
    return {0, "OUT_OF_STOCK"}
end

-- 4) 발급 처리
redis.call("DECR", KEYS[1])
redis.call("SADD", KEYS[2], ARGV[1])

return {1, "ISSUED"}