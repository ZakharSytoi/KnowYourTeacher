import {Component, Input, OnInit} from '@angular/core';
import {ReviewDto} from "../../models/ReviewDto";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../services/auth.service";
import {AsyncPipe, DatePipe, NgIf, NgStyle} from "@angular/common";
import {LikeDislikeService} from "../../services/like-dislike.service";
import {BehaviorSubject, concatMap, map} from "rxjs";
import {RouterLink} from "@angular/router";
import {LikeOnReviewResponseDto} from "../../models/likedislike/LikeOnReviewResponseDto";

interface ReviewState {
    likeCount: number,
    isLiked: boolean,
    dislikeCount: number,
    isDisliked: boolean
}

@Component({
    selector: 'app-simple-review',
    templateUrl: './simple-review.component.html',
    standalone: true,
    imports: [
        DatePipe,
        RouterLink,
        AsyncPipe,
        NgIf,
        NgStyle
    ],
    styleUrl: './simple-review.component.scss'
})
export class SimpleReviewComponent implements OnInit {
    @Input() review!: ReviewDto;
    @Input() bgColor: string = "#ebfafe"
    state$!: BehaviorSubject<ReviewState>;


    constructor(readonly http: HttpClient,
                readonly authService: AuthService,
                readonly likeDislikeService: LikeDislikeService) {

    }

    ngOnInit(): void {
        this.state$ = new BehaviorSubject<ReviewState>({
            likeCount: this.review.likeCount,
            isLiked: this.review.isLiked,
            dislikeCount: this.review.dislikeCount,
            isDisliked: this.review.isDisliked
        })
    }

    updateState(params: {
        likeCount?: number,
        isLiked?: boolean,
        dislikeCount?: number,
        isDisliked?: boolean
    }) {
        const currentState = this.state$?.getValue();
        this.state$.next({
            ...currentState,
            ...params
        })
    }

    handleLike(reviewId: number): void {
        const currentState = this.state$?.getValue();
        if (currentState?.isDisliked) {
            this.likeDislikeService.unDislike$(reviewId).pipe(concatMap((response) => {
                    this.updateState({
                        isDisliked: response.isDisliked,
                        dislikeCount: response.numberOfDislikes
                    })
                    return this.likeDislikeService.like$(reviewId).pipe(
                        map((response) => this.updateState({
                            isLiked: response.isLiked,
                            likeCount: response.numberOfLikes
                        }))
                    )
                }
            )).subscribe();
        } else {
            this.likeDislikeService.like$(reviewId).pipe(
                map((response: LikeOnReviewResponseDto) => {
                    this.updateState({isLiked: response.isLiked, likeCount: response.numberOfLikes})
                })
            ).subscribe();
        }
    }

    handleUnlike(reviewId: number): void {
        console.log("Handle like happens")
        this.likeDislikeService.unLike$(reviewId).pipe(
            map((response: LikeOnReviewResponseDto) => {
                this.updateState({isLiked: response.isLiked, likeCount: response.numberOfLikes})
            })
        ).subscribe()
    }

    handleDislike(reviewId: number): void {
        const currentState = this.state$?.getValue();
        if (currentState?.isLiked) {
            this.likeDislikeService.unLike$(reviewId).pipe(concatMap((response) => {
                    this.updateState({
                        isLiked: response.isLiked,
                        likeCount: response.numberOfLikes
                    })
                    return this.likeDislikeService.dislike$(reviewId).pipe(
                        map((response) => this.updateState({
                            isDisliked: response.isDisliked,
                            dislikeCount: response.numberOfDislikes
                        }))
                    )
                }
            )).subscribe();
        } else {
            this.likeDislikeService.dislike$(reviewId).pipe(
                map((response) => {
                    this.updateState({
                        isDisliked: response.isDisliked,
                        dislikeCount: response.numberOfDislikes
                    })
                })
            ).subscribe();
        }
    }

    handleUnDislike(reviewId: number): void {
        console.log("Handle like happens")
        this.likeDislikeService.unDislike$(reviewId).pipe(
            map((response) => {
                this.updateState({
                    isDisliked: response.isDisliked,
                    dislikeCount: response.numberOfDislikes
                })
            })
        ).subscribe()
    }


    // handleLikeOrDislike(url: string, i: number, likeOrDislike: boolean) {
    //     this.http.post(url, '').subscribe({
    //         error: error => console.log(error)
    //     })
    //     const item = this.likesDislikes[i];
    //
    //     if (likeOrDislike) {
    //         if (item.isLiked) {
    //             item.likes--;
    //             item.isLiked = false;
    //         } else {
    //             item.likes++;
    //             item.isLiked = true;
    //
    //             if (item.isDisliked) {
    //                 item.dislikes--;
    //                 item.isDisliked = false;
    //             }
    //         }
    //     } else {
    //         if (item.isDisliked) {
    //             item.dislikes--;
    //             item.isDisliked = false;
    //         } else {
    //             item.dislikes++;
    //             item.isDisliked = true;
    //
    //             if (item.isLiked) {
    //                 item.likes--;
    //                 item.isLiked = false;
    //             }
    //         }
    //     }
    // }
}
