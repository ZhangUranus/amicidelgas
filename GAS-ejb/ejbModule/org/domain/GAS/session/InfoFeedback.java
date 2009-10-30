package org.domain.GAS.session;

public class InfoFeedback {
		
		public InfoFeedback() {}
		
		public InfoFeedback(String c, int f) {
			comment = c; feedback = f;
		}

		private String comment;
		private int feedback;
		
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public int getFeedback() {
			return feedback;
		}
		public void setFeedback(int feedback) {
			this.feedback = feedback;
		}
	}