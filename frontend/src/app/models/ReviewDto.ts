export interface ReviewDto {
  id: number;
  score: number;
  nickname:string,
  universityName:string,
  subjectName: string;
  reviewText: string;
  createdDate: Date;
  likeCount: number;
  dislikeCount: number;
  likeLink: string;
  dislikeLink: string;
  isLiked: boolean;
  isDisliked: boolean;
}