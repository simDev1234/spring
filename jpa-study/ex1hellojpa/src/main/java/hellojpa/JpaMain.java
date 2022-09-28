package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        /*  <persistence-unit name="hello"> */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        /* 트랜잭션 시작 */
        tx.begin();

        try {

            // 저장
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("여름이");
//            em.persist(member);

            // 조회
            Member findMember = em.find(Member.class, 2L);
            System.out.println("name : " + findMember.getName());

            // 삭제
            // em.remove(findMember);

            // 수정
            findMember.setName("어미냥이");

            /* 트랜잭션 커밋 */
            tx.commit();
        } catch (Exception e) {
            System.out.println("roll back");
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
