package SE_team.IssueManager.controller;

/*
 * @AutoConfigureMockMvc
 * 
 * @SpringBootTest
 * 
 * @NoArgsConstructor
 * 
 * @Transactional
 * class MemberControllerTest {
 * 
 * @Autowired
 * private MemberService memberService;
 * 
 * 
 * @Autowired
 * private MockMvc mockMvc;
 * private ObjectMapper mapper = new ObjectMapper();
 * 
 * 
 * @Test
 * void 회원가입() throws Exception{
 * 
 * String pw="1234";
 * String memberId= "seoyeon2";
 * Role role=Role.ADMIN;
 * 
 * MemberRequestDto.SignUpRequestDTO
 * request=MemberRequestDto.SignUpRequestDTO.builder()
 * .pw(pw)
 * .memberId(memberId)
 * .role(role).build();
 * 
 * 
 * String body=mapper.writeValueAsString(request);
 * 
 * ResultActions
 * action=mockMvc.perform(MockMvcRequestBuilders.post("/members/sign-up")
 * .content(body)
 * .contentType("application/json"));
 * 
 * action.andExpect(result -> {
 * MockHttpServletResponse response=result.getResponse();
 * System.out.println("응답:"+response.getContentAsString());
 * });
 * 
 * 
 * Member findMem=memberService.findMemberById(1L).get();
 * assertEquals(findMem.getMemberId(),memberId);
 * 
 * }
 * 
 * @Test
 * void 중복이메일()throws Exception{ //아직 미완성 (예외처리 작업중)..
 * 
 * String pw="1234";
 * String memberId="spring";
 * Role role=Role.ADMIN;
 * 
 * MemberRequestDto.SignUpRequestDTO
 * request1=MemberRequestDto.SignUpRequestDTO.builder()
 * .pw(pw)
 * .memberId(memberId)
 * .role(role).build();
 * MemberRequestDto.SignUpRequestDTO
 * request2=MemberRequestDto.SignUpRequestDTO.builder()
 * .pw(pw)
 * .memberId(memberId)
 * .role(role).build();
 * 
 * 
 * String body1=mapper.writeValueAsString(request1);
 * String body2=mapper.writeValueAsString(request2);
 * 
 * ResultActions
 * action=mockMvc.perform(MockMvcRequestBuilders.post("/members/sign-up")
 * .content(body1)
 * .contentType("application/json"));
 * 
 * 
 * action.andExpect(result -> {
 * MockHttpServletResponse response=result.getResponse();
 * System.out.println(response.getContentAsString());
 * });
 * 
 * ServletException e=assertThrows(ServletException.class,
 * ()->mockMvc.perform(MockMvcRequestBuilders.post("/members/sign-up")
 * .content(body2)
 * .contentType("application/json")));
 * //assertEquals(e.getMessage(),"이미 존재하는 회원입니다.");
 * 
 * }
 * }
 * 
 */