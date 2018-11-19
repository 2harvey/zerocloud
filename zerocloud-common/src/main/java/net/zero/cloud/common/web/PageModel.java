package net.zero.cloud.common.web;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PageModel<T> {
	private List<T> list;
	private Integer pageNum;
	private Integer pageNo;
	private Integer pageSize;
	private Long total;
	


}
