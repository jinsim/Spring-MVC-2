package hello.login.domain.member;

import hello.login.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {
    // 원래는 인터페이스로 만드는 것이 낫다. 구현체를 통해 DB와 메모를 왔다갔다할 수 있으니

    private static Map<Long, Member> store = new HashMap<>(); // static 사용
    private static long sequence = 0L; // static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member); // 저장소에 저장
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        // Optional을 하면, 값이 null일 때, 에러 없이 null을 반환해준다.

        /*
        List<Member> all = findAll();
        for (Member m : all) {
            if (m.getLoginId().equals(loginId)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
        */

        // 스트림을 활용한다. 위의 주석문과 동일하다.
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 저장소에서 Value만 리스트로 묶어서 반환
    }

    public void clearStore() {
        store.clear();
    }
}
