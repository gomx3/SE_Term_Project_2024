package SE_team.IssueManager.service;

/*
 * @SpringBootTest
 * 
 * @Transactional
 * class MemberServiceTest {
 * 
 * @Autowired MemberService memberService;
 * 
 * @Autowired MemberRepository memberRepository;
 * 
 * @Test
 * void 회원가입() throws Exception{
 * 
 * //Given
 * MemberRequestDto.SignUpRequestDTO member =
 * MemberRequestDto.SignUpRequestDTO.builder()
 * .memberId("spring")
 * .pw("1234")
 * .role(Role.ADMIN).build();
 * 
 * //When
 * Member saveMem=memberService.signUp(member);
 * 
 * // System.out.println("member:"+saveMem.getId());
 * 
 * // Optional<Member> findMember=memberService.findMemberById(saveMem.getId());
 * // if (findMember.isPresent()) {
 * // System.out.println(findMember.get().getEmail());
 * // }
 * // else{
 * // System.out.println("cannot find member");
 * // }
 * 
 * //Then
 * Member findMember=memberService.findMemberById(saveMem.getId()).get();
 * assertEquals(saveMem.getId(),findMember.getId());
 * }
 * 
 * @Test
 * void 중복이메일_확인(){
 * //Given
 * MemberRequestDto.SignUpRequestDTO member1 =
 * MemberRequestDto.SignUpRequestDTO.builder()
 * .memberId("spring")
 * .pw("1234")
 * .role(Role.ADMIN).build();
 * 
 * MemberRequestDto.SignUpRequestDTO member2 =
 * MemberRequestDto.SignUpRequestDTO.builder()
 * .memberId("spring")
 * .pw("1234")
 * .role(Role.ADMIN).build();
 * 
 * //When
 * memberService.signUp(member1);
 * IllegalStateException e=assertThrows(IllegalStateException.class,
 * ()->memberService.signUp(member2));
 * assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
 * 
 * }
 * }
 * 
 */