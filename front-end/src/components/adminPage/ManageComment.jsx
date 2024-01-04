import React from "react";

const ManageComment = () => {
    return (
        <>
            <main>
                <div class="header">
                    <div class="left">
                        <h3>Manage Comments</h3>
                    </div>
                    <div class="time">
                        <span>March 2023 - February 2024</span>
                    </div>
                </div>
            </main>

            <div class="db-content col">
                <div class="left">
                    <span class="top">Total comments</span>
                        <h3>10.0k</h3>

                    <span>Growth in comments on this year</span>
                </div>
            </div>
            <hr class="h" />
            <section id="testimonials">
                <div class="testimonial-box-container">
                    <div class="testimonial-box">
                        <div class="box-top">
                            <div class="profile">
                                <div class="profile-img">
                                    <img src="./images/avatar.jpg" />
                                </div>
                                <div class="name-user">
                                    <strong>Quach Duc Huy</strong>
                                    <span>@DucHuy</span>
                                    <span>28-12-2023</span>
                                </div>
                            </div>
                            <div class="reviews">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="far fa-star"></i>
                            </div>
                        </div>

                        <div class="client-comment">
                            <p>Lorem ipsum dolor sit amet consectetur, adipisicing elit. Quisquam iusto iure similique quod omnis ipsum exercitationem velit eum veritatis repellat saepe delectus laborum voluptas architecto eligendi assumenda dolores, provident voluptatem!</p>

                        </div>
                    </div>
                </div>
            </section>
        </>)
}

export default ManageComment