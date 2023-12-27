export interface ReviewDto {
  id: number;
  score: number;
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