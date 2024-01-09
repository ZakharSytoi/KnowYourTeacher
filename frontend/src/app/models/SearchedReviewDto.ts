export interface SearchedReviewDto {
  id: number,
  teacherName:string,
  teacherSurname:string,
  teacherId: string,
  score: number,
  nickname:string,
  universityName:string,
  subjectName: string,
  reviewText: string,
  createdDate: Date,
  likeCount: number,
  dislikeCount: number,
  likeLink: string,
  dislikeLink: string,
  isLiked: boolean,
  isDisliked: boolean
}