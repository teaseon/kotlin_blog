package com.example.kotlin_blog.domain.member


import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.support.PageableUtils
import org.springframework.data.support.PageableExecutionUtils


interface MemberRepository : JpaRepository<Member, Long>, MemberCustomRepository {

}

interface MemberCustomRepository {
    fun findMembers(pageable: Pageable): Page<Member>
}



class MemberCustomRepositoryImpl (
    private val queryFactory: SpringDataQueryFactory
) : MemberCustomRepository {


    override fun findMembers (pageable: Pageable)
    : Page<Member> {

        val memberPage = queryFactory.listQuery<Member> {
            select(entity(Member::class))
            from(entity(Member::class))
            fetch(Member::posts)
            limit(pageable.pageSize)
            offset(pageable.offset.toInt())
            orderBy(ExpressionOrderSpec(column(Member::id), true))
        }

        val count = queryFactory.listQuery<Member> {
            select(entity(Member::class))
            from(entity(Member::class))
        }

        return PageableExecutionUtils.getPage(memberPage, pageable){
            count.size.toLong()
        }
    }

}